package com.example.takhfifdar.util

class ServerNotRespondingException(): Exception("Servers are not responding...")
class AccessDeniedException(): Exception("Access denied...")
class NotSuchContentException(): Exception("404 returned...")
