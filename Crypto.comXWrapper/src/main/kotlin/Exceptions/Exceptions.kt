package Exceptions

import java.lang.Exception

class CoinSymbolNotFoundException(val symbol:String) : Exception("No such coin symbol: " + symbol)

class PeriodNotFoundException(val period:Int) : Exception("The following ticker period not available: $period minutes")

class CryptoComServerResException() : Exception("The format returned by Crypto.com server is not in expected format")

class NotAuthenticatedException() : Exception("Please populate api key and api secret")