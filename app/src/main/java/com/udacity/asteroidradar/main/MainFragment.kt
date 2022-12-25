package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private lateinit var Adapter: Adapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.asteroidRecycler.adapter = Adapter(OnClickAsteroid{asteroid->
            asteroid.let { findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid)) }
        }).apply {
            Adapter = this
        }
        viewModel.asteroids.observe(viewLifecycleOwner){
            Adapter.Asteroids = it
        }
        viewModel.Image.observe(viewLifecycleOwner) {
            it?.let {Image ->
                Picasso.get().load(Image.url).into(binding.activityMainImageOfTheDay)
                binding.activityMainImageOfTheDay.contentDescription = Image.title
                binding.dailyPhotoText.text = Image.title
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_asteroids-> viewModel.weaklyAsteroids.observe(viewLifecycleOwner) {
                Adapter.Asteroids = it
            }
            R.id.show_today_asteroids -> viewModel.dailyAsteroids.observe(viewLifecycleOwner) {
                Adapter.Asteroids = it
            }
            R.id.show_saved_asteroids -> viewModel.asteroids.observe(viewLifecycleOwner) {
                Adapter.Asteroids = it
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
