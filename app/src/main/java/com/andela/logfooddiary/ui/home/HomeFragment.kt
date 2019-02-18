package com.andela.logfooddiary.ui.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andela.logfooddiary.R
import com.andela.logfooddiary.ui.FoodDiary.FoodDiaryFragment
import com.andela.logfooddiary.ui.FoodDiary.SearchLogsFragment
import com.andela.logfooddiary.ui.photoUpload.PhotoActivity
import com.andela.logfooddiary.utils.InjectorUtils
import com.andela.logfooddiary.viewmodel.DiaryViewmodel
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    private lateinit var viewmodel: DiaryViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        food_diary_button.setOnClickListener { _ -> getDateDialog() }
        button_photo_upload.setOnClickListener { _ -> openPhotoUpload() }
        search_log.setOnClickListener { _ -> getSearchDateDialog() }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    private fun openFoodDiaryFragment(){
        activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, FoodDiaryFragment.newInstance())
                .commit()
    }

    private fun openSearchFragment(){
        activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, SearchLogsFragment.newInstance())
                .commit()
    }

    private fun openPhotoUpload(){
        Intent(activity!!, PhotoActivity::class.java).apply {
            startActivity(this)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateDialog() {
        val dateListener =
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val format = SimpleDateFormat("MM-dd-yyyy")
                    val date = calendar.time
                    val formattedDate = format.format(date)
                    viewmodel.foodRecord = formattedDate
                    viewmodel.foodDate = date.time.toString()
                    openFoodDiaryFragment()
                }
        val dateDialog = DatePickerDialog(activity!!, dateListener, 1990, 1, 1)
        dateDialog.show()
    }

    @SuppressLint("SimpleDateFormat")
    fun getSearchDateDialog() {
        val dateListener =
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val format = SimpleDateFormat("MM-dd-yyyy")
                    val date = calendar.time
                    val formattedDate = format.format(date)
                    viewmodel.selectedSearchDate = formattedDate
                    openSearchFragment()
                }
        val dateDialog = DatePickerDialog(activity!!, dateListener, 1990, 1, 1)
        dateDialog.show()
    }



    private fun initViewModel() {
        val factory = InjectorUtils.provideViewModelFactory()
        viewmodel = ViewModelProviders
                .of(activity!!, factory).get(DiaryViewmodel::class.java)
    }


}
