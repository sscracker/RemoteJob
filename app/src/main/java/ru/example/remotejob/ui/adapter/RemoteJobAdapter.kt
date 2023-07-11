package ru.example.remotejob.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import ru.example.remotejob.databinding.ItemJobBinding
import ru.example.remotejob.models.Job
import ru.example.remotejob.ui.fragments.MainFragmentDirections

class RemoteJobAdapter:RecyclerView.Adapter<RemoteJobAdapter.RemoteJobViewHolder>(){


    private var binding: ItemJobBinding? = null

    class RemoteJobViewHolder(val binding: ItemJobBinding) : ViewHolder(binding.root)


      private val diffCallback = object : DiffUtil.ItemCallback<Job>(){
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }

    }
    val diff = AsyncListDiffer(this@RemoteJobAdapter,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobViewHolder {
       // return RemoteJobViewHolder(ItemJobBinding.inflate(LayoutInflater.from(parent.context),parent,false)!!)
        binding = ItemJobBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RemoteJobViewHolder(binding!!)
    }

    override fun getItemCount() = diff.currentList.size

    override fun onBindViewHolder(holder: RemoteJobViewHolder, position: Int) {
        val currentJob = diff.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(currentJob.companyLogoUrl)
                .into(binding?.ivCompanyLogo!!)

            binding?.tvCompanyName?.text = currentJob.companyName
            binding?.tvJobLocation?.text = currentJob.candidateRequiredLocation
            binding?.tvJobTitle?.text = currentJob.title
            binding?.tvJobType?.text = currentJob.jobType

            val date = currentJob.publicationDate?.split("T")
            binding?.tvDate?.text = date?.get(0)

        }.setOnClickListener { view ->
            val direction = MainFragmentDirections.actionMainFragmentToJobDetailsFragment(currentJob)

            view.findNavController().navigate(direction)
        }
    }
}