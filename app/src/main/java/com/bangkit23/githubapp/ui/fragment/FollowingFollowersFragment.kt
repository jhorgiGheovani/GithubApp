package com.bangkit23.githubapp.ui.fragment

import android.os.Bundle
import android.text.style.TtsSpan.ARG_USERNAME
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit23.githubapp.R
import com.bangkit23.githubapp.data.Result
import com.bangkit23.githubapp.data.remote.response.GithubDetailsResponse
import com.bangkit23.githubapp.data.remote.response.ItemsItem
import com.bangkit23.githubapp.databinding.FragmentFollowingFollowersBinding
import com.bangkit23.githubapp.ui.adapter.MainAdapter
import com.bangkit23.githubapp.ui.viewmodel.MainViewModel
import com.bangkit23.githubapp.ui.viewmodel.ViewModelFactory

class FollowingFollowersFragment : Fragment() {


    private lateinit var binding: FragmentFollowingFollowersBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    // declare position and username properties
    private var position: Int = 0
    private var username: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowingFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Context RecyclerView
        binding.rvFragment.layoutManager = LinearLayoutManager(requireActivity())

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1){
            mainViewModel.getUserFollowing(username!!).observe(viewLifecycleOwner){ result->
                if(result!=null){
                    when(result){
                        is Result.Loading->{
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success->{
                            binding.progressBar.visibility = View.GONE
                            val newsData = result.data
//                            Log.d("Result Following",newsData.toString())
                            setUserData(newsData)
                        }
                        is Result.Error->{
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Something went wrong: ${result.error}",
                                Toast.LENGTH_SHORT).show()
                            Log.d("ERROR FROM FOLLOWING", result.error)
                        }
                    }
                }
            }
        }
        else {
            mainViewModel.getUserFollowers(username!!).observe(viewLifecycleOwner){ result->
                if(result!=null){
                    when(result){
                        is Result.Loading->{
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success->{
                            binding.progressBar.visibility = View.GONE
                            val newsData = result.data
                            setUserData(newsData)
                        }
                        is Result.Error->{
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Something went wrong: ${result.error}",
                                Toast.LENGTH_SHORT).show()
                            Log.d("ERROR FROM FOLLOWERS", result.error)
                        }
                    }
                }
            }
        }

    }

    private fun setUserData(userSearch: List<ItemsItem>){
        val getUserData = userSearch.map {
            ItemsItem(it.login, it.id)
        }
        val adapter = MainAdapter(getUserData)
        binding.rvFragment.adapter =adapter
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

}