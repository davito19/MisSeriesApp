package com.davito.misseriesapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.davito.misseriesapp.databinding.FragmentProfileBinding
import com.davito.misseriesapp.ui.login.SignInActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this)[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textViewName
//        profileViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        profileViewModel.loadUserInfo()

        profileViewModel.errorMsg.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireActivity(), errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        profileViewModel.userLoaded.observe(viewLifecycleOwner) {user ->
            with(binding){
                textViewName.text = user?.name
                emailTextView.text = user?.email
                genreTextView.text = user?.genreFavoritos
            }
        }

        profileViewModel.isSignOut.observe(viewLifecycleOwner){
            startActivity(Intent(activity, SignInActivity::class.java))
            activity?.finish()
        }

        binding.signoutbutton.setOnClickListener {
            profileViewModel.signOut()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}