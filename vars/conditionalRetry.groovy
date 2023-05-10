def call(Integer maxRetries, closure, body) {
    def config = [:]
    def retries = 0
    body.resolveStrategy = Closure.OWNER_FIRST
    body.delegate = config
    def c = closure.clone()
    c.delegate = this

    retry (maxRetries) {
        if (retries == 0 || c()) {
            body()
        } else {
            error("Build doesn't fulfill conditional to retry.")
        }
        retries++
    }
}
