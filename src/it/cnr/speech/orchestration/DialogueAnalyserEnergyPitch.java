package it.cnr.speech.orchestration;

import java.io.File;

import org.apache.commons.io.FileUtils;

import it.cnr.speech.audiofeatures.SyllabicEnergyPitchExtractor;
import it.cnr.speech.segmentation.Energy;

public class DialogueAnalyserEnergyPitch {

		public void cleanFolder(File folder) throws Exception {
		if (folder.exists() && folder.listFiles().length > 0)
			FileUtils.deleteDirectory(folder);
	}

	float maxSilence;
	float pitchWindowSec;
	float energyWindowSec;
	
	
	public DialogueAnalyserEnergyPitch(float maxSilence, float pitchWindowSec, float energyWindowSec) {
		
		this.maxSilence = maxSilence;
		this.pitchWindowSec = pitchWindowSec;
		this.energyWindowSec = energyWindowSec;
	}

	public void executeTaggingWorkflowEnergyPitch(File audioFile) throws Exception {

		long t00 = System.currentTimeMillis();
		File outputFolder = new File(audioFile.getParentFile(),"output");
		long t0 = 0;
		long t1 = 0;

		cleanFolder(outputFolder);
		System.out.println("Temporary folder " + outputFolder);
		System.out.println("1- Segment based on silence energy ("+maxSilence+"s)");
		t0 = System.currentTimeMillis();
		Energy energy = new Energy();
		energy.setWindowIns(maxSilence);
		energy.segmentSignal(audioFile, outputFolder);
		t1 = System.currentTimeMillis();
		System.out.println("Finished in " + ((float) (t1 - t0) / 1000f) + "s");

		System.out.println("2- Extract Pitch and Energy from segments, using syllabic-size windows (energy:"+energyWindowSec+"s; pitch:"+pitchWindowSec+"s)");
		t0 = System.currentTimeMillis();
		SyllabicEnergyPitchExtractor epe = new SyllabicEnergyPitchExtractor(energyWindowSec, pitchWindowSec);
		epe.extractEnergyPitchOfWaveFiles(outputFolder);
		t1 = System.currentTimeMillis();
		System.out.println("Finished in " + ((float) (t1 - t0) / 1000f) + "s");

		long t11 = System.currentTimeMillis();
		System.out.println("All done. Elapsed:" + ((float) (t11 - t00) / 1000f) + "s");

	}


}
