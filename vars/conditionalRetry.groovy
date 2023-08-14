def call(Integer maxRetries, String filename, String[] match, Integer sleep, body) {
    def config = [:]
    def retries = 0
    def sleep = 0 
    body.resolveStrategy = Closure.OWNER_FIRST
    body.delegate = config

    retry (maxRetries) {
        if (retries == 0 || match.any{el -> readFile(filename).contains(el)}) {
            sleep = retries == 0 ? 0 : 15
            retries += 1
            body()
        } else {
            error("Build doesn't fulfill conditional to retry.")
        }
    }
}
