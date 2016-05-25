# Hypercube = Promises + RetroFit2 + OkHttp3

Project level gradle

     allprojects {
	  	repositories {
			  maven { url "https://jitpack.io" }
  		}
	  }
	  

    dependencies {
		   compile 'com.github.neurospeech:android-hypercube:v1.08'
	  }


Initialization - In Application class

	HypercubeApplication.init(this);
	
Service Example

        public class APIService : RestService{
        
        	
        
        	interface API{
        	
        		@POST("/login")
        		Promise<Result> login(@Body FormInput input);
        		
        	
        	}
        }
		
Service is now independent of OkHttp library, so your code can stay independent of underlying implementation. It is also very easy to create a mock by simply inheriting MockService and implementing API interface.		
        
Service Usage

	APIService service;
	service.login(...)
		.then(new IResultListener<Result>(){
			void onResult(Promise<Result> promise){
			
				if(promise.getError()!=null){
					// error.. something went wrong...
				}else{
					// success...
				}
			
			}
		});

