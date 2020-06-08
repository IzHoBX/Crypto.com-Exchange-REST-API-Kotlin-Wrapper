package Exceptions

import java.lang.Exception

class CoinSymbolNotFoundException(val symbol:String) : Exception("No such coin symbol: " + symbol)

class TradeTypeNotFoundException(val s:String) : Exception("Invlaid trade type: $s. It can only be \"buy\" or \"sell\"")

class PeriodNotFoundException(val period:Int) : Exception("The following ticker period not available: $period minutes")

class CryptoComServerResException() : Exception("The format returned by Crypto.com server is not in expected format")

class NotAuthenticatedException() : Exception("Please populate api key and api secret")