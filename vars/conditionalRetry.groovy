def call(Integer maxRetries = 5, String filename, String[] match, Integer retrySleep = 10, Boolean expBackoff = true, body) {
    def config = [:]
    def retries = 0
    body.resolveStrategy = Closure.OWNER_FIRST
    body.delegate = config

    retry (maxRetries) {
        if (retries == 0 || match.any{el -> readFile(filename).contains(el)}) {
            if (retries > 0 && retrySleep > 0) {
                if (expBackoff) {
                    sleep(retrySleep * retries)
                } else {
                    sleep(retrySleep)
                }
            }
            retries += 1
            body()
        } else {
            error("Skipping retry : build doesn't fulfill condition to retry.")
        }
    }
}
