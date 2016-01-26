package demo

import io.jsonwebtoken.Jwts

import authentikat.jwt.*
import io.jsonwebtoken.impl.crypto.MacProvider
import java.util.*

fun main(args: Array<String>) {

    val key = MacProvider.generateKey()
    val keyBase64EncodedString = Base64.getUrlEncoder().encodeToString(key.encoded)
    println(">>GenerateKey: $keyBase64EncodedString")

    ////////////////////////////////////////////////////
    val prodKey = "hzz/XCah+YVk6jpcjDBLOUrLhM47asAbkJ3aCZpQmgiIZPHiZknR051CTpK/X6f2h1pC4cZEo1eXuFuJBEQ4YA=="
    val token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdXN0b21lcjFAZXhhbXBsZS5jb20iLCJzZXNzaW9uX2N0eCI6eyJ1c2VyaWQiOjMsInVzZXJuYW1lIjoiY3VzdG9tZXIxQGV4YW1wbGUuY29tIiwiZnVsbG5hbWUiOm51bGwsInBlcm1zIjpbXSwicm9sZXMiOltdfSwiaWF0IjoxNDUzNzg2MDUzfQ.KZfU-ldt3OKiNUWsUIW7bpTRzOnNO5MxDpZqFaUxuZg"

    val claims = Jwts.parser().setSigningKey(prodKey).parseClaimsJws(token).getBody()
    println(">>Claims by Jwts: $claims")

    val isValid = JsonWebToken.validate(token, prodKey) // why false ??
    println(">>JsonWebToken isValid: $isValid")
}
