# Hypercube = Promises + RetroFit2 + OkHttp3 + Jackson

- Jackson customization
- RetroFit Error parsing in body
- Lightweight Promise<T> instead of Call<T>
- Promise allows other async computation encapsulation such as bitmap conversion etc
- Promise.whenAll
- Display busy animation while http call is in progress


## Project level gradle

     allprojects {
	  	repositories {
			  maven { url "https://jitpack.io" }
  		}
	  }
	  

    dependencies {
		   compile 'com.github.neurospeech:android-hypercube:v1.38'
	  }


### Initialization - In Application class

	HypercubeApplication.init(this);
	
### Service Example

        public class APIService : RestService{
        
        	
        
        	interface API{
        	
        		@POST("/login")
        		Promise<Result> login(@Body FormInput input);
        		
        	
        	}
        }
		
Service is now independent of OkHttp library, so your code can stay independent of underlying implementation. It is also very easy to create a mock by simply inheriting MockService and implementing API interface.		
        
### Service Usage

	apiService.login(...)
		.then(new IResultListener<Result>(){
			void onResult(Promise<Result> promise){
			
				if(promise.getError()!=null){
					// error.. something went wrong...
				}else{
					// success...
					Result result = promise.getResult();
				}
			
			}
		});

## Promise Usage

	Promise<Bitmap> promise = 
		TaskPromise.runAsync(inputFile, new IFunction<File,Bitmap>(){

			@override
			public Bitmap run(File input){
				return BitmapFactory.loadFrom(input);
			}

		});

	promise.onResult(new IResultListener<Bitmap>(){
		void onResult(Promise<Bitmap> p){
			if(p.getError() != null){
				// something went wrong, display error
			}else{
				Bitmap bitmap = p.getResule();
			}
		}
	})

## Promise.whenAll

	Promise.whenAll(...)
		.then(new IResultListener(){
			void onResult(Promise<String> p){
				if(p.getError() != null){
					// atleast one of promise failed...
				}else{
					// all promises finished successfully without any error
				}
			}
		});

# HyperRecyclerView

- Headers/Footers
- Item headers with HeaderedAdapter

