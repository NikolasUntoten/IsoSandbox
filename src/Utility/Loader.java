package Utility;

import java.util.ArrayList;
import java.util.ServiceLoader;

import game.Module;

public class Loader {
	
	public static <T> T load(Class<T> t) {
		ServiceLoader<T> loader = getLoader(t);
		T[] arr = loaderToArray(loader);
		return arr[0];
	}
	
	public static <T> ServiceLoader<T> getLoader(Class<T> t) {
		ServiceLoader<T> loader = ServiceLoader.load(t);
		return loader;
	}
	
	public static <T> T[] loaderToArray(ServiceLoader<T> loader) {
		//Get number of modules
		int count = 0;
		for (T t : loader) {
			count++;
		}
		
		//Move modules from loader to array
		T[] arr = (T[]) new Object[count];
		int i = 0;
		for (T t : loader) {
			arr[i] = t;
			i++;
		}
		
		return arr;
	}
	
	public static Module[] loadModules() {
		ServiceLoader<Module> moduleLoader = ServiceLoader.load(Module.class);
		ArrayList<Module> modules = new ArrayList<Module>();
		for (Module m : moduleLoader) {
			modules.add(m);
		}
		return modules.toArray(new Module[0]);
	}
}
