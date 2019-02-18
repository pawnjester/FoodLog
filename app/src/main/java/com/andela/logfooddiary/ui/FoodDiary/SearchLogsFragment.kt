package com.andela.logfooddiary.ui.FoodDiary

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.andela.logfooddiary.R
import com.andela.logfooddiary.data.Food
import com.andela.logfooddiary.utils.InjectorUtils
import com.andela.logfooddiary.viewmodel.DiaryViewmodel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_search_logs.*


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchLogsFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference

    private lateinit var viewmodel: DiaryViewmodel

    private lateinit var searchFood: ArrayList<Food>


    private val listAdapter by lazy {
        DisplaySearchAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        val database = FirebaseDatabase.getInstance()
        databaseReference = database.reference
        searchFood = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(display_search_logs) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false)
            adapter = listAdapter
        }
        getItems(viewmodel.selectedSearchDate ?: "")
    }


    private fun getItems(date: String) {
        // select * from Food where date = 13-12-300
        val query = databaseReference.orderByChild("date").equalTo(date)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity!!, databaseError.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //similar to a normal java forloop
                for (snap in dataSnapshot.children){
                    val food = snap.getValue(Food::class.java)
                    searchFood.add(food ?: Food())
                        listAdapter.updateList(searchFood)
                }
            }

        })
    }


    private fun initViewModel() {
        val factory = InjectorUtils.provideViewModelFactory()
        viewmodel = ViewModelProviders
                .of(activity!!, factory).get(DiaryViewmodel::class.java)
    }



    companion object {
        fun newInstance() = SearchLogsFragment()
    }


}
