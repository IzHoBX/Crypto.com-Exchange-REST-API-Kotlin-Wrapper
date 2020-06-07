package Exceptions

import java.lang.Exception

class CoinSymbolNotFoundException(val symbol:String) : Exception("No such coin symbol: " + symbol)

class CryptoComServerResException() : Exception("The format returned by Crypto.com server is not in expected format")