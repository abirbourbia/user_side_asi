package com.example.userside

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.userside.databinding.FragmentMessageVocaleBinding
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File
import okhttp3.RequestBody

@Suppress("DEPRECATION")
class MessageVocaleFragment : Fragment() {

    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    private lateinit var binding : FragmentMessageVocaleBinding
    private lateinit var voice_recorder : ImageButton
    private lateinit var counter : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.FOREGROUND_SERVICE),0)
        }
        mediaRecorder = MediaRecorder()
        output = requireContext().getExternalFilesDir(null)?.absolutePath + "/recording.mp3"
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setOutputFile(output)
        var recorder = 0
        binding = FragmentMessageVocaleBinding.inflate(inflater, container,false)
        val view = binding.root
        voice_recorder = binding.voicerecorder
        voice_recorder.background = ContextCompat.getDrawable(requireContext(), R.drawable.round_button_off)
        counter = binding.counter

        voice_recorder.setOnClickListener{
            if (recorder == 0)
            {
                recorder = 1
                voice_recorder.background = ContextCompat.getDrawable(requireContext(), R.drawable.round_button_on)
                voice_recorder.setImageResource(R.drawable.pause)
                if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.FOREGROUND_SERVICE)
                    ActivityCompat.requestPermissions(requireActivity(), permissions,0)
                } else {
                    startRecording()
                }
            } else {
                recorder = 0
                voice_recorder.background = ContextCompat.getDrawable(requireContext(), R.drawable.round_button_off)
                voice_recorder.setImageResource(R.drawable.voice)
                stopRecording()
                val audioFile = File(output)
                val audioRequestBody = RequestBody.create("audio/*".toMediaTypeOrNull(), audioFile)
                val audioPart = MultipartBody.Part.createFormData("audioFile", audioFile.name, audioRequestBody)
                println("lets checccccccckkkkkkk "+audioPart)

            }
        }
        return view
    }

    // function to start recording
    private fun startRecording() {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
            Toast.makeText(context, "Recording started!", Toast.LENGTH_SHORT).show()
        }

    // function to stop recording
    private fun stopRecording(){
        if(state){
            println(output)
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
        }else{
            Toast.makeText(context, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }
}