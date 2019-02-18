package com.andela.logfooddiary.ui.FoodDiary

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.andela.logfooddiary.R
import com.andela.logfooddiary.data.Food
import com.andela.logfooddiary.utils.InjectorUtils
import com.andela.logfooddiary.viewmodel.DiaryViewmodel
import kotlinx.android.synthetic.main.fragment_food_diary.*


/**
 * A simple [Fragment] subclass.
 *
 */
class FoodDiaryFragment : Fragment() {

    private lateinit var viewmodel: DiaryViewmodel
    private var mood: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_food_diary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        today_date_text.text = viewmodel.foodRecord
        radio_group.setOnCheckedChangeListener({ group, checkedId ->
            val rb = group.findViewById(checkedId) as RadioButton
            mood = rb.text.toString()
        })

            fab.setOnClickListener { _ ->
                saveToDatabase()
            }
    }

    // similar to static values or variables in Java, in kotlin, you use companion objects
    companion object {
        fun newInstance() = FoodDiaryFragment()
    }

    private fun saveToDatabase() {
        val breakfast = breakfast_text.text.toString()
        val lunch = lunch_text.text.toString()
        val dinner = dinner_text.text.toString()

        if (breakfast.isNotEmpty()
                && lunch.isNotEmpty()
                && dinner.isNotEmpty()
                && radio_group.checkedRadioButtonId != -1) {
            val food = Food(
                    breakfast = breakfast,
                    lunch = lunch,
                    dinner = dinner,
                    mood = mood,
                    date = viewmodel.foodRecord)
            viewmodel.addFood(food)

            //this is for moving to another fragment from a fragment
            activity!!.supportFragmentManager
                    .beginTransaction().replace(R.id.fragment_container,
                            DisplayLogFragment.newInstance()).commit()
        } else {
            Toast.makeText(activity!!, "Please enter the required fields",
                    Toast.LENGTH_LONG).show()
        }


    }


    private fun initViewModel() {
        val factory = InjectorUtils.provideViewModelFactory()
        viewmodel = ViewModelProviders
                .of(activity!!, factory).get(DiaryViewmodel::class.java)
    }


}
