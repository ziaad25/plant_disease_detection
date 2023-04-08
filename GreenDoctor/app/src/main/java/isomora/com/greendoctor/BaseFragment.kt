package isomora.com.greendoctor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    abstract val bindingInflater: (LayoutInflater) -> VB
    private var _binding: VB? = null
    lateinit var sharedPreferences: SharedPreferences
    lateinit var data: Any

    protected val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater(layoutInflater)
        hasBackButtonOrNot()
        setActionBarTitle()
        return _binding!!.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun loadFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

   /* fun <T : Parcelable> createArgument(value: T, fragment: Fragment) =
        fragment.apply {
            arguments = Bundle().apply {
                putParcelable(Constants.Keys.ARGUMENT, value)
            }
        }*/

    abstract fun hasBackButtonOrNot()

    abstract fun setActionBarTitle()


}
