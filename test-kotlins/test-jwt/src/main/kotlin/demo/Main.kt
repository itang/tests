package demo

import io.jsonwebtoken.Jwts

import authentikat.jwt.*
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.crypto.MacProvider
import java.util.*

fun main(args: Array<String>) {
    val key = MacProvider.generateKey(SignatureAlgorithm.HS256)
    println(">>key.algorithm ${key.algorithm}")
    val keyBase64EncodedString = Base64.getUrlEncoder().encodeToString(key.encoded)
    println(">>GenerateKey: $keyBase64EncodedString")

    ////////////////////////////////////////////////////
    val prodKey = "uLkvkYvgiA01ozKoTvyyXL_YBZUxDQK0OGosXmdBg84="
    val token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdXN0b21lcjFAZXhhbXBsZS5jb20iLCJzZXNzaW9uX2N0eCI6eyJ1c2VyaWQiOjMsInVzZXJuYW1lIjoiY3VzdG9tZXIxQGV4YW1wbGUuY29tIiwiZnVsbG5hbWUiOm51bGwsInBlcm1zIjpbXSwicm9sZXMiOltdfSwiaWF0IjoxNDUzODAxMjczfQ.iwghjrT2efWaXltTuig-mRN7jMcQUOdLePuWozVedms"

    val claims = Jwts.parser().setSigningKey(prodKey).parseClaimsJws(token).body
    println(">>Claims by Jwts: $claims")

    val isValid = JsonWebToken.validate(token, prodKey) // why false ??
    println(">>JsonWebToken isValid: $isValid")
}
