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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_display_log.*


/**
 * A simple [Fragment] subclass.
 *
 */
class DisplayLogFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference

    private lateinit var viewmodel: DiaryViewmodel

    private lateinit var logs: ArrayList<Food>

    private lateinit var auth: FirebaseAuth

    private val listAdapter by lazy {
        DisplayLogAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        val database = FirebaseDatabase.getInstance()
        databaseReference = database.reference
        logs = ArrayList()
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_display_log, container, false)
    }

    companion object {
        fun newInstance() = DisplayLogFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(display_log_recycler) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false)
            adapter = listAdapter
        }
        getItems(auth.currentUser?.email ?: "")
    }

    private fun initViewModel() {
        val factory = InjectorUtils.provideViewModelFactory()
        viewmodel = ViewModelProviders
                .of(activity!!, factory).get(DiaryViewmodel::class.java)
    }

    private fun getItems(email: String) {
        val query = databaseReference.orderByChild("user").equalTo(email)
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity!!, databaseError.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snap in dataSnapshot.children){
                    val food = snap.getValue(Food::class.java)
                    logs.add(food ?: Food())
                    listAdapter.updateList(logs)
                }
            }

        })
    }

}
