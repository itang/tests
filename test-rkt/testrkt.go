// Copyright 2016, itang. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

// testrkt.go [created: Wed, 10 Feb 2016]

package main

import "fmt"
import "log"
import "net/http"

func main() {
    fmt.Println("Hello, itang!")

    http.HandleFunc("/", func(w http.ResponseWriter, r * http.Request){
        log.Printf("requset from %v\n", r.RemoteAddr);
        w.Write([]byte("hello\n"))
    })

    log.Fatal(http.ListenAndServe(":5000", nil))
}
