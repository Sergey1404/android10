package com.example.lab10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab10.R
import com.example.lab10.WeatherNW
import com.example.lab10.databinding.ItemWeatherWarmHolderBinding
import com.example.lab10.databinding.ItemWeatherColdHolderBinding
import kotlinx.android.synthetic.main.item_weather_cold_holder.view.*
import java.lang.IllegalArgumentException
//Todo
private const val TYPE_COLD = 0
private const val TYPE_WARM = 1

class WeatherAdapter: ListAdapter<WeatherNW.DataWeather, RecyclerView.ViewHolder>(WeatherDiffCallback()){

    class WeatherViewHolder(view:View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        when(viewType){
            TYPE_COLD -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_cold_holder, parent, false)
                return  WeatherViewHolder(view)
            }
            TYPE_WARM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_warm_holder, parent, false)
                return  WeatherViewHolder(view)
            }
            else ->{
                throw IllegalArgumentException("Missing type of holder")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_COLD -> (holder as WeatherColdHolder).bind(getItem(position))
            TYPE_WARM -> (holder as WeatherWarmHolder).bind(getItem(position))
            else -> throw IllegalArgumentException("Invalid temperature")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}

class Weather :
    ListAdapter<WeatherNW.DataWeather, RecyclerView.ViewHolder>(WeatherDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_COLD -> {
                val bindingCold = ItemWeatherColdHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return WeatherColdHolder(bindingCold)
            }
            TYPE_WARM -> {
                val bindingWarm = ItemWeatherWarmHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return WeatherWarmHolder(bindingWarm)
            }
            else -> {
                throw IllegalArgumentException("Missing type of holder")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_COLD -> (holder as WeatherColdHolder).bind(getItem(position))
            TYPE_WARM -> (holder as WeatherWarmHolder).bind(getItem(position))
            else -> throw IllegalArgumentException("Invalid temperature")
        }
    }

    override fun getItemViewType(position: Int): Int = if (getItem(position).main.temp < -20) {
        TYPE_COLD
    } else {
        TYPE_WARM
    }
}

private fun getImage(weather: WeatherNW.DataWeather) =
    "https://openweathermap.org/img/wn/${weather.weather.first().icon}@2x.png"

class WeatherColdHolder(private val binding: ItemWeatherColdHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: WeatherNW.DataWeather) {
        val weatherTimeDate = weather.dtTxt.split(" ")
        binding.tvTemperature.text =
            binding.root.context.getString(R.string.temperature, weather.main.temp.toString())
        binding.tvTime.text = weatherTimeDate.first()
        binding.tvDate.text = weatherTimeDate.last()
        binding.tvPressure.text = weather.main.pressure.toString()
        Glide
            .with(binding.root)
            .load(getImage(weather))
            .into(binding.ivIcon)
    }
}

class WeatherWarmHolder(private val binding: ItemWeatherWarmHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: WeatherNW.DataWeather) {
        val weatherTimeDate = weather.dtTxt.split(" ")
        binding.tvTemperature.text =
            binding.root.context.getString(R.string.temperature, weather.main.temp.toString())
        binding.tvTime.text = weatherTimeDate.first()
        binding.tvDate.text = weatherTimeDate.last()
        binding.tvPressure.text = weather.main.pressure.toString()
        Glide
            .with(binding.root)
            .load(getImage(weather))
            .into(binding.ivIcon)
    }
}

class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherNW.DataWeather>() {
    override fun areItemsTheSame(
        oldItem: WeatherNW.DataWeather,
        newItem: WeatherNW.DataWeather
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: WeatherNW.DataWeather,
        newItem: WeatherNW.DataWeather
    ): Boolean = oldItem == newItem
}