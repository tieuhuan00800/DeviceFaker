package com.devicefaker

import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.net.wifi.WifiInfo
import com.highcapable.yukihookapi.hook.factory.YukiHookAPI

class MainHook : YukiHookAPI {
    override fun onHook() {
        val profile = RandomProfile.generateProfile()

        encase {
            // Fake Build fields
            replaceField(Build::class.java, "MODEL", profile["model"])
            replaceField(Build::class.java, "BRAND", profile["brand"])
            replaceField(Build::class.java, "MANUFACTURER", profile["manufacturer"])
            replaceField(Build::class.java, "SERIAL", profile["serial"])

            // Fake Android ID
            injectMember {
                method { 
                    name = "getString"
                    paramCount = 2 
                }
                afterHook {
                    if (args[1] == Settings.Secure.ANDROID_ID) result = profile["android_id"]
                }
            }

            // Fake IMEI
            replaceMethod(TelephonyManager::class.java, "getDeviceId", profile["imei"])
            replaceMethod(TelephonyManager::class.java, "getImei", profile["imei"])

            // Fake Subscriber ID
            replaceMethod(TelephonyManager::class.java, "getSubscriberId", profile["subscriber_id"])

            // Fake Phone number
            replaceMethod(TelephonyManager::class.java, "getLine1Number", profile["phone_number"])

            // Fake WiFi MAC
            replaceMethod(WifiInfo::class.java, "getMacAddress", profile["mac"])
        }
    }
}

// Helper extension
fun <T> com.highcapable.yukihookapi.hook.param.HookParam.replaceField(clazz: Class<*>, fieldName: String, value: String?) {
    injectMember {
        field { 
            name = fieldName
            inClass = clazz 
        }
        replaceTo(value)
    }
}

fun <T> com.highcapable.yukihookapi.hook.param.HookParam.replaceMethod(clazz: Class<*>, methodName: String, value: String?) {
    injectMember {
        method { 
            name = methodName
            inClass = clazz 
        }
        replaceTo(value)
    }
}