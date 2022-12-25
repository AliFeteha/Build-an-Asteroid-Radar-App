package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.SingleAstreoidBinding

class Adapter(val onClickAsteroid: OnClickAsteroid)
    : RecyclerView.Adapter<Holder>(){
    var Asteroids:List<Asteroid> = emptyList()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding: SingleAstreoidBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),Holder.layout, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, pos: Int) {
        holder.bind.also {
            it.asteroid = Asteroids[pos]
            it.asteroidClick = onClickAsteroid
        }
    }
    override fun getItemCount(): Int = Asteroids.size
}
class OnClickAsteroid(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}
class Holder(val bind:SingleAstreoidBinding):RecyclerView.ViewHolder(bind.root) {
    companion object{
        var layout = R.layout.single_astreoid
    }
}
