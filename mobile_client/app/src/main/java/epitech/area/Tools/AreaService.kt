package epitech.area.Tools

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.google.gson.Gson
import epitech.area.Activities.ReActionActivity
import epitech.area.Managers.AreaAuthorization
import epitech.area.Storages.AReActionObject
import epitech.area.Storages.AccountObject
import epitech.area.Storages.AreaObject
import epitech.area.Storages.ReactionObject

class AreaService {

    val gson = Gson()

    private object Holder { val INSTANCE = AreaService() }

    companion object {
        val instance: AreaService by lazy { AreaService.Holder.INSTANCE }
    }

    fun getAreas(areaAdapter: AreaAdapter) {
        try {
            "area/".httpGet()
                    .responseObject(AreaObject.ArrayDeserializer()) { _, _, result ->
                        val (res, err) = result
                        if (err == null) {
                            areaAdapter.setAreas(res!!)
                        }
                    }
        } catch (e: Exception) {
            Log.d("getAreas Exception", e.toString())
        }
    }

    fun getArea(areaAdapter: AreaAdapter, position: Int, uniqueId: String) {
        try {
            ("area/" + uniqueId).httpGet()
                    .responseObject(AreaObject.Deserializer()) { _, _, result ->
                        val (res, err) = result
                        if (err == null) {
                            areaAdapter.setArea(res!!, position)
                        }
                    }
        } catch (e: Exception) {
            Log.d("getArea Exception", e.toString())
        }
        FuelManager.instance.basePath = "http://10.0.2.2:8080/" //remove this when using real server
    }

    fun getArea(reActionAdapter: ReActionAdapter, textView: TextView,  area: AreaObject) {
        try {
            ("area/" + area.uniqueId).httpGet()
                    .responseObject(AreaObject.Deserializer()) { _, _, result ->
                        val (res, err) = result
                        if (err == null) {
                            textView.text = res!!.name
                            area.name = res.name
                            area.timer = res.timer
                            area.activated = res.activated
                            reActionAdapter.setReActions(res)
                        }
                    }
        } catch (e: Exception) {
            Log.d("getArea Exception", e.toString())
        }
    }

    fun changeAreaInfos(area: AreaObject) {
        ("area/" + area.uniqueId).httpPut().body(gson.toJson(area))
    }

    fun createArea(name: String = "New Area", activated: Boolean = true, timer: Int = 60) {
        try {
            "area/".httpPost()
                    .body("{\"name\": \"" + name + "\", \"activated\": \"" + activated + "\", \"timer\": \"" + timer + "\"}")
        } catch (e: Exception) {
            Log.d("Create Area Exception", e.toString())
        }
    }

    fun deleteArea(areaId: String) {
        try {
            ("area/" + areaId).httpDelete()
        } catch (e: Exception) {
            Log.d("Delete Area Exception", e.toString())
        }
    }

    fun postReAction(reAction: AReActionObject) {
        try {
            if (reAction.id.isNotBlank())
                ("area/" + reAction.areaId  + "/" + reAction.id).httpPut().body(gson.toJson(reAction))
            else
                ("area/" + reAction.areaId  + "/").httpPost().body(gson.toJson(reAction))
        } catch (e: Exception) {
            Log.d("Create Area Exception", e.toString())
        }
    }

    fun deleteReaction(reaction: ReactionObject) {
        try {
            ("area/" + reaction.areaId  + "/" + reaction.id).httpDelete()
        } catch (e: Exception) {
            Log.d("Create Area Exception", e.toString())
        }
    }

    fun getAccounts(accountAdapter: AccountAdapter) {
        FuelManager.instance.basePath = "" //remove this when using real server
        FuelManager.instance.baseHeaders = mapOf() //remove this when using real server
        try {
            "https://next.json-generator.com/api/json/get/Vk_WtKgrU".httpGet()
                    .responseObject(AccountObject.ArrayDeserializer()) { _, _, result ->
                        val (res, err) = result
                        if (err == null) {
                            accountAdapter.setAccounts(res!!)
                        }
                    }
        } catch (e: Exception) {
            Log.d("getAreas Exception", e.toString())
        }
        FuelManager.instance.basePath = "http://10.0.2.2:8080/" //remove this when using real server
    }

    fun getServiceAccounts(serviceName: String, reActionActivity: ReActionActivity) {
        FuelManager.instance.basePath = "" //remove this when using real server
        FuelManager.instance.baseHeaders = mapOf() //remove this when using real server
        try {
            "https://next.json-generator.com/api/json/get/Vk_WtKgrU".httpGet()
                    .responseObject(AccountObject.ArrayDeserializer()) { _, _, result ->
                        val (res, err) = result
                        if (err == null) {
                            reActionActivity.setAccounts(res!!)
                            reActionActivity.updateAccountStep()
                        }
                    }
        } catch (e: Exception) {
            Log.d("getAreas Exception", e.toString())
        }
        FuelManager.instance.basePath = "http://10.0.2.2:8080/" //remove this when using real server
    }

    fun deleteAccount(acountId: String) {
        return
        "".httpDelete()
    }

    fun changeFuelHeaders(applicationContext: Context) {
        val token : String = AreaAuthorization.instance.getAccessToken(applicationContext)
        if (token.isNotEmpty()) {
            FuelManager.instance.baseHeaders = mapOf(
                    "Content-Type" to "application/json; charset=UTF-8",
                    "Authorization" to token)
        } else {
            FuelManager.instance.baseHeaders = mapOf(
                    "Content-Type" to "application/json; charset=UTF-8")
        }
    }
}
