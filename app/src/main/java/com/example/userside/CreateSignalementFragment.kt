package com.example.userside

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.userside.databinding.FragmentCreateSignalementBinding


class CreateSignalementFragment : Fragment() {

    private lateinit var binding: FragmentCreateSignalementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateSignalementBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}