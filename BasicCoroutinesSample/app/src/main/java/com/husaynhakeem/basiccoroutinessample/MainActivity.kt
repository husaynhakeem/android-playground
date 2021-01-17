package com.husaynhakeem.basiccoroutinessample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.husaynhakeem.basiccoroutinessample.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var jobs: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start/Cancel both jobs
        binding.startOrCancelJobsButton.setOnClickListener { startOrCancelJobs() }
    }

    private fun startOrCancelJobs() {
        if (::jobs.isInitialized && jobs.isActive) {
            cancelJobs()
        } else {
            startJobs()
        }
    }

    private fun cancelJobs() {
        log("Cancel jobs")
        jobs.cancel()
    }

    private fun startJobs() {
        lifecycleScope.launch(Dispatchers.Default) {
            log("Start jobs")
            onJobsStarted()
            jobs = if (binding.sequentialRadioButton.isChecked) {
                startJobsSequentially()
            } else {
                startJobsConcurrently()
            }
            jobs.invokeOnCompletion {
                log("Jobs have completed: $it")
                onJobsCompleted()
            }
        }
    }

    private suspend fun startJobsSequentially(): Job {
        return lifecycleScope.launch(Dispatchers.Default) {
            log("Start jobs sequentially")

            // Launch Job1 and wait for it to complete
            log("Start job #1")
            val job1 = launch { runJob1(this) }
            job1.join()
            log("Finished job #1")

            // Launch Job2 and wait for it to complete
            log("Start job #2")
            val job2 = launch { runJob2(this) }
            job2.join()
            log("Finished job #2")
        }
    }

    private suspend fun startJobsConcurrently(): Job {
        return lifecycleScope.launch(Dispatchers.Default) {
            log("Start jobs Concurrently")

            // Launch both jobs in a non blocking manner, so that they both run in parallel
            log("Start job #1")
            launch { runJob1(this) }

            log("Start job #2")
            launch { runJob2(this) }
        }
    }

    /** Runs a coroutine that tales 5 seconds to complete. */
    private suspend fun runJob1(scope: CoroutineScope) {
        var progress = 0
        while (scope.isActive && progress < MAX_PROGRESS) {
            log("Job #1 running with progress $progress")
            delay(DELAY)
            progress += JOB_1_STEP
            updateJob1Progress(progress)
        }
    }

    /** Runs a coroutine that tales 4 seconds to complete. */
    private suspend fun runJob2(scope: CoroutineScope) {
        var progress = 0
        while (scope.isActive && progress < MAX_PROGRESS) {
            log("Job #2 running with progress $progress")
            delay(DELAY)
            progress += JOB_2_STEP
            updateJob2Progress(progress)
        }
    }

    private fun updateJob1Progress(progress: Int) {
        lifecycleScope.launchWhenCreated {
            binding.job1ProgressBar.progress = progress
        }
    }

    private fun updateJob2Progress(progress: Int) {
        lifecycleScope.launchWhenCreated {
            binding.job2ProgressBar.progress = progress
        }
    }

    private fun onJobsStarted() {
        lifecycleScope.launchWhenCreated {
            binding.startOrCancelJobsButton.setText(R.string.label_cancel_jobs)
            updateJob1Progress(0)
            updateJob2Progress(0)
        }
    }

    private fun onJobsCompleted() {
        lifecycleScope.launchWhenCreated {
            binding.startOrCancelJobsButton.setText(R.string.label_start_jobs)
        }
    }

    private fun log(message: String) {
        println(message)
    }

    companion object {
        private const val JOB_1_STEP = 20
        private const val JOB_2_STEP = 25
        private const val MAX_PROGRESS = 100
        private const val DELAY = 1_000L //  1 second
    }
}