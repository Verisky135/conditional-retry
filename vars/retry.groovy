def call(int maxRetries, boolean condition, body) {
    def config = [:]
    def retries = 0
    body.resolveStrategy = Closure.OWNER_FIRST
    body.delegate = config

    retry (maxRetries) {
        if (retries == 0 || condition) {
            body()
        } else {
            error("Build doesn't fulfill conditional to retry.")
        }
        retries++
    }
}
