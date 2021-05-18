package org.deebug.starwarscharacters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.deebug.starwarscharacters.models.SWCharacter
import org.deebug.starwarscharacters.adapters.SWCharacterListAdapter
import org.json.JSONArray
import org.json.JSONObject

/**
 * Author: John N. Mote
 * Date Created: 17/05/2021
 * */

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    private val listOfCharacters = mutableListOf<SWCharacter>()
    private val swCharacterListAdapter: SWCharacterListAdapter = SWCharacterListAdapter(this)
    private var currentApiUrl: String = "https://swapi.dev/api/people/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fetch data for the first time
        fetchDataFromApi(currentApiUrl)
        initializations()

        // handle when the user scrolls to the bottom of the page
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int){
                if(!recyclerView.canScrollVertically(1)){
                    // scrolled to bottom
                    if(currentApiUrl.startsWith("http"))
                        fetchDataFromApi("https:" + currentApiUrl.split(":")[1])
                }
            }
        })

    }



    // Function to initialisations
    fun initializations(){
        recyclerView = findViewById(R.id.recycler_view)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.adapter = swCharacterListAdapter
        swCharacterListAdapter.setSWCharacterList(listOfCharacters)
    }

    // Temporary function used to display dummy data for testing purposes
    private fun generateDummyData(): List<SWCharacter>{
        val listOfCharacters = mutableListOf<SWCharacter>()

        var swCharacter = SWCharacter("John Mote", "Male", "12.2")
        listOfCharacters.add(swCharacter)

        swCharacter = SWCharacter("Lisa Mote", "Female", "11.2")
        listOfCharacters.add(swCharacter)

        return listOfCharacters
    }


    // Function to fetch data from api and populate the list of SWCharacters
    private fun fetchDataFromApi(url: String){
        var obj: JSONObject
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {response ->

                obj = JSONObject(response)
                val people: JSONArray = obj.getJSONArray("results")
                var i = 0
                listOfCharacters.clear()
                while(i < people.length()){

                    val swCharacter = SWCharacter(
                        people.getJSONObject(i).getString("name"),
                        people.getJSONObject(i).getString("gender"),
                        people.getJSONObject(i).getString("height")
                    )
                    listOfCharacters.add(swCharacter)
                    //println(people.getJSONObject(i).getString("name")) // log
                    i++
                }
                swCharacterListAdapter.addMoreData(listOfCharacters)
                // update url to load from
                currentApiUrl = obj.getString("next")
                //queue?.cancelAll("star_wars")

            },
            {error ->
                println(error)
            }
        )
        stringRequest.tag = "star_wars"
        queue?.add(stringRequest)

    }
}