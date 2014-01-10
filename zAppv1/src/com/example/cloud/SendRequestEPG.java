package com.example.cloud;
import com.google.gson.annotations.SerializedName;


public class SendRequestEPG {

	//channel, Period , programId

	@SerializedName("live")
	public Live live;
	// tester String si int bug

	public SendRequestEPG(){
		live = new Live();
		// live.livePeriod = livePeriod;
		// live.liveTVChannelsId = liveTVChannelsId;
		// live.programId = programId;
	}

	public class Live {

		@SerializedName ("livePeriod")
		public int livePeriod;

		@SerializedName ("liveTVChannelsId")
		public int liveTVChannelsId;

		@SerializedName ("programId")
		public int programId;

		// a period = 0 --- http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live
		void setPeriod(int period){
			this.livePeriod = period;
		}
		
		//http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?TVChannelsId=1,2
		void setChannelId(int channel){
			this.liveTVChannelsId = channel;
		}
		
		//http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?programId=93622270
		void setProgramId(int program){
			this.programId = program;
		}
		
		//http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?period=5&TVChannelsId=2,3,7
		void setPeriodChannel(int period, int channel){
			this.livePeriod = period;
			this.liveTVChannelsId = channel;
		}
		
		
	}

}
