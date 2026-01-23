package com.example.app

import io.fluxzero.sdk.web.ServeStatic
import org.springframework.stereotype.Component

@Component
@ServeStatic("/")
class UiEndpoint 
