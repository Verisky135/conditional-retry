def call(Integer maxRetries, String filename, String[] match, Integer retrySleep = 15, body) {
    def config = [:]
    def retries = 0
    body.resolveStrategy = Closure.OWNER_FIRST
    body.delegate = config

    retry (maxRetries) {
        if (retries == 0 || match.any{el -> readFile(filename).contains(el)}) {
            if (retries > 0) {
                sleep(retrySleep)
            }
            retries += 1
            body()
        } else {
            error("Build doesn't fulfill conditional to retry.")
        }
    }
}
