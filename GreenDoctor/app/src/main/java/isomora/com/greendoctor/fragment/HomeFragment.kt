package isomora.com.greendoctor.fragment

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import isomora.com.greendoctor.R
import isomora.com.greendoctor.base.BaseFragment
import isomora.com.greendoctor.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate


    override fun hasBackButtonOrNot() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun setActionBarTitle() {
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
    }

    override fun menuLogout() {
        TODO("Not yet implemented")
    }


}