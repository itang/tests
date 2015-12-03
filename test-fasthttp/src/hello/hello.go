package main

import "log"
import "fmt"

import "github.com/valyala/fasthttp"

func main() {
    // The server will listen for incoming requests on this address.
    listenAddr := "127.0.0.1:8080"

    // This function will be called by the server for each incoming request.
    //
    // RequestCtx provides a lot of functionality related to http request
    // processing. See RequestCtx docs for details.
    requestHandler := func(ctx *fasthttp.RequestCtx) {
        //fmt.Fprintf(ctx, "Hello, world! Requested path is %q", ctx.Path())
        fmt.Fprintln(ctx, "Hello, world!")
    }

    // Start the server with default settings.
    // Create Server instance for adjusting server settings.
    //
    // ListenAndServe returns only on error, so usually it blocks forever.
    if err := fasthttp.ListenAndServe(listenAddr, requestHandler); err != nil {
        log.Fatalf("error in ListenAndServe: %s", err)
    }
}