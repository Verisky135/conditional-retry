def call(Integer maxRetries, String filename, String[] match, body) {
    def config = [:]
    def retries = 0
    body.resolveStrategy = Closure.OWNER_FIRST
    body.delegate = config

    retry (maxRetries) {
        def isMatch = match.any{el -> readFile(filename).contains(el)}
        println isMatch
        if (retries == 0 || match.any{el -> readFile(filename).contains(el)}) {
            body()
        } else {
            error("Build doesn't fulfill conditional to retry.")
        }
        retries++
    }
}
