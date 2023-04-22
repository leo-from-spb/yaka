package lb.yaka.test.launcher

import org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage
import org.junit.platform.launcher.Launcher
import org.junit.platform.launcher.LauncherDiscoveryRequest
import org.junit.platform.launcher.TagFilter.includeTags
import org.junit.platform.launcher.TestPlan
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
import org.junit.platform.launcher.core.LauncherFactory
import org.junit.platform.launcher.listeners.SummaryGeneratingListener
import org.junit.platform.launcher.listeners.TestExecutionSummary


object TestLaunch {

    @JvmStatic
    fun main(args: Array<String>) {

        if (args.isEmpty()) {
            blame("Specify the tag expression\n")
            return
        }
        if (args.size > 1) {
            blame("Too many arguments.\nSpecify the tag expression as the first argument.\n")
            return
        }

        launchTests(args[0])
    }

    @JvmStatic
    private fun launchTests(tagExpressions: String) {
        say("Tag expression: $tagExpressions")
        val request: LauncherDiscoveryRequest = LauncherDiscoveryRequestBuilder.request()
            .selectors(selectPackage("lb.yaka.test"))
            .filters(includeTags(tagExpressions))
            .build()
        val launcher: Launcher = LauncherFactory.create()
        val testPlan: TestPlan = launcher.discover(request)
        val summaryListener = SummaryGeneratingListener()
        launcher.registerTestExecutionListeners(summaryListener)
        launcher.execute(testPlan)
        val summary: TestExecutionSummary = summaryListener.summary

        val message = "\n" +
                      "========== SUMMARY ==========\n" +
                      "  All found containers:  ${summary.containersFoundCount}\n" +
                      "  Started containers:    ${summary.containersStartedCount}\n" +
                      "  Successful containers: ${summary.containersSucceededCount}\n" +
                      "  Skipped containers:    ${summary.containersSkippedCount}\n" +
                      "  Failed containers:     ${summary.containersFailedCount}\n" +
                      "  Aborted containers:    ${summary.containersAbortedCount}\n" +
                      "-----------------------------\n" +
                      "  All found tests:       ${summary.testsFoundCount}\n" +
                      "  Started tests:         ${summary.testsStartedCount}\n" +
                      "  Successful tests:      ${summary.testsSucceededCount}\n" +
                      "  Skipped tests:         ${summary.testsSkippedCount}\n" +
                      "  Failed tests:          ${summary.testsFailedCount}\n" +
                      "  Aborted tests:         ${summary.testsAbortedCount}\n" +
                      "-----------------------------\n"

        say(message)
        Thread.sleep(10L)

        val totalOkCount = summary.testsSucceededCount
        val totalFailureCount = summary.totalFailureCount
        when {
            totalOkCount == 0L      -> {
                val message1 = if (summary.testsFoundCount == 0L) "No tests found." else "All tests failed."
                halt(message1, errorCode = 0x10)
            }
            totalFailureCount == 0L -> {
                say("OK\n")
            }
            else                    -> {
                halt("Total $totalFailureCount failures.", errorCode = 0x02)
            }
        }
    }

}
