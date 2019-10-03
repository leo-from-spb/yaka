package lb.yaka.utils


infix fun CharSequence.match(pattern: Regex): MatchResult? = pattern.matchEntire(this)
