package lb.yaka.base.gears

import lb.yaka.base.Yaka


sealed class Controller {

    open fun<X: Any> handle(subject: Subject<X>,
                            expectationDescription: String,
                            checkFunction: CheckFunction<X>): Subject<X> {
        val result = subject.checkFunction()
        when (result) {
            is Success -> return subject
            is Fail    -> { report(subject.x, subject.id, expectationDescription, result.problem); return skeleton }
        }
    }

    open fun <X: Any, Y: Any> handleAlteration(subject: Subject<X>,
                                               expectationDescription: String,
                                               checkFunction: CheckAlterFunction<X, Y>): Subject<Y> {
        val result: Result<Y> = subject.checkFunction()
        when (result) {
            is Product -> return subject.alter(result.value)
            is Success -> throw RuntimeException("Unexpected state: returned class ${result.javaClass.simpleName} instead of Product<R>")
            is Fail    -> { report(subject.x, subject.id, expectationDescription, result.problem); return skeleton }
        }
    }

    internal abstract fun report(subjectValue: Any?, subjectId: EntityId, expectationDescription: String, problemDescription: String)

}



object DirectController : Controller() {

    override fun report(subjectValue: Any?, subjectId: EntityId, expectationDescription: String, problemDescription: String) {
        val message = prepareReportMessage(subjectId, expectationDescription, subjectValue, problemDescription)
        reportMessage(message)
    }

    internal fun reportMessage(message: CharSequence) {
        Yaka.fail(message = message.toString())
    }

}


class AggregatingController(private val origin: Controller,
                            private val useElementInfo: Boolean = false) : Controller() {
    
    private val problems: MutableList<ProblemInfo> = mutableListOf()

    internal val hasProblems: Boolean get() = problems.isNotEmpty()

    override fun report(subjectValue: Any?, subjectId: EntityId, expectationDescription: String, problemDescription: String) {
        problems += ProblemInfo(subjectValue, subjectId, expectationDescription, problemDescription)
    }

    internal fun reportMulti(problems: Collection<ProblemInfo>) {
        this.problems += problems
    }

    internal fun<X: Any> flush(subject: Subject<X>): Subject<X> {
        if (problems.isEmpty()) {
            return subject
        }
        else {
            when (origin) {
                is DirectController      -> flushToDirect(origin, subject)
                is AggregatingController -> flushToAggregate(origin)
                else                     -> { /* just for Kotlin compiler */ }
            }
            return skeleton
        }
    }

    private fun flushToDirect(controller: DirectController, subject: Subject<*>) {
        when (val n = problems.size) {
            0 -> {
                /* all ok */
            }
            1 -> {
                if (useElementInfo) reportManyProblems(subject, n, controller)
                else reportSingleProblem()
            }
            else -> {
                reportManyProblems(subject, n, controller)
            }
        }
    }

    private fun reportSingleProblem() {
        val p = problems.first()
        origin.report(p.subjectValue, p.subjectId, p.expectationDescription, p.problemDescription)
    }

    private fun reportManyProblems(subject: Subject<*>, n: Int, controller: DirectController) {
        val buff = StringBuilder()
        buff.append(subject.id.simpleName).append(": $n expectations failed\n")
        buff.append("Actual: ").append(subject.x.toString()).append('\n')
        var k = 0
        for (p in problems) {
            k++
            val displayName = p.subjectId.nameInContext(subject.id)
            buff.append("\t+--- ---- $k ---- ---+\n")
            buff.append("\tElement:  ").append(displayName).append('\n')
            buff.append("\tActual:   ").append(p.subjectValue).append('\n')
            buff.append("\tExpected: ").append(p.expectationDescription).append('\n')
            buff.append("\tProblem:  ").append(p.problemDescription).append('\n')
        }
        buff.append("\t+--- ---- - ---- ---+\n")
        controller.reportMessage(buff)
    }

    private fun flushToAggregate(controller: AggregatingController) {
        controller.reportMulti(problems)
    }

}



object Oblivion : Controller() {
    override fun <X : Any> handle(subject: Subject<X>, expectationDescription: String, checkFunction: CheckFunction<X>): Subject<X> = skeleton
    override fun <X: Any, Y: Any> handleAlteration(subject: Subject<X>, expectationDescription: String, checkFunction: CheckAlterFunction<X, Y>): Subject<Y> = skeleton
    override fun report(subjectValue: Any?, subjectId: EntityId, expectationDescription: String, problemDescription: String) {}
}




internal data class ProblemInfo (val subjectValue: Any?,
                                 val subjectId: EntityId,
                                 val expectationDescription: String, 
                                 val problemDescription: String)


internal val skeleton = Subject<Nothing>(null, EntityId(type = "Ancient Skeleton", name = null), Oblivion)
