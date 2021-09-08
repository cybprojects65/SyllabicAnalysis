package it.cnr.speech.main;

import java.io.File;

import it.cnr.speech.orchestration.DialogueAnalyserEnergyPitch;

public class MainEnergyPitchAnalysis {

	
	public static void main(String[] args) throws Exception{
			
			File audio = new File("./example/audioCut.wav");
			float maxSilence = 0.1f; // s
			float pitchWindowSec = 0.2f; // s
			float energyWindowSec = 0.1f; // s
			
			//output will be in example/output
			DialogueAnalyserEnergyPitch dt = new DialogueAnalyserEnergyPitch( maxSilence,  pitchWindowSec,  energyWindowSec);
			dt.executeTaggingWorkflowEnergyPitch(audio);
	}
	
	
}
