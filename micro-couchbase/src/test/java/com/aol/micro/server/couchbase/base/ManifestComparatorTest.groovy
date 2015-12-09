package com.aol.micro.server.couchbase.base;

import static org.junit.Assert.*
import static org.mockito.Mockito.*
import groovy.transform.CompileStatic

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import com.aol.micro.server.couchbase.DistributedMapClient
import com.fasterxml.jackson.core.JsonParseException



@CompileStatic
class ManifestComparatorTest {

	ManifestComparator manifestComparator
	DistributedMapClient mock
	String key = "key"

	@Before
	public void setup(){
		mock = Mockito.mock(DistributedMapClient)
		manifestComparator  = new ManifestComparator(key,mock)
	}

	@Test
	public void newKeyScenario(){
		Mockito.when(mock.get(key)).thenReturn(Optional.empty())
		assert manifestComparator.isOutOfDate()
	}
	@Test
	public void newKeySavesAsVersion1(){
		Mockito.when(mock.get(key)).thenReturn(Optional.empty())
		
		VersionedKey version = 	new VersionedKey("key",1L)
		
		manifestComparator.saveAndIncrement("data")
		Mockito.verify(mock,times(1)).put(eq(version.withVersion(1L).toJson()),any(Data))
		Mockito.verify(mock).put(key,version.withVersion(1L).toJson())
		assert manifestComparator.versionedKey == version.withVersion(1L).toJson()
	}
	
	@Test
	public void testIsOutofDateFalse() {
		VersionedKey version = 	new VersionedKey("key",2L)
		Mockito.when(mock.get(key)).thenReturn(Optional.of(version.toJson()))
		assert manifestComparator.isOutOfDate()
	}

	@Test
	public void testIsOutofDateTrue() {
		Mockito.when(mock.get(key)).thenReturn(Optional.of(manifestComparator.@versionedKey))
		assert !manifestComparator.isOutOfDate()
	}

	@Test
	public void testLoad() {
		VersionedKey version = 	new VersionedKey("key",2L)
		Mockito.when(mock.get(key)).thenReturn(Optional.of(version.toJson()))
		Mockito.when(mock.get(version.toJson())).thenReturn(Optional.of(new Data(["hello"], new Date(),"hello")))
		manifestComparator.load()
		List<String> result = (List<String>)manifestComparator.getData()
		assert result.get(0) == "hello"
	}

	@Test(expected=JsonParseException)
	public void testChangeMidLoad() {
		Mockito.when(mock.get(key)).thenReturn(Optional.of("v1"))
		Mockito.when(mock.get("v1")).thenReturn(Optional.empty())
		manifestComparator.load()
		List<String> result =(List<String>) manifestComparator.getData()
		fail("should not reach here")
	}


	@Test
	public void "when succesful save, version is incremented"(){
		VersionedKey version = 	new VersionedKey("key",1L)
		Mockito.when(mock.get(key)).thenReturn(Optional.of(version.toJson()))
		manifestComparator.saveAndIncrement("data")
		Mockito.verify(mock,times(1)).put(eq(version.withVersion(2L).toJson()),any(Data))
		Mockito.verify(mock).put(key,version.withVersion(2L).toJson())
		assert manifestComparator.versionedKey == version.withVersion(2L).toJson()
	}
	@Test
	public void "when succesful save, old version deleted"(){
		VersionedKey version = 	new VersionedKey("key",1L)
		Mockito.when(mock.get(key)).thenReturn(Optional.of(version.toJson()))
		manifestComparator.saveAndIncrement("data")

		Mockito.verify(mock).delete(version.toJson())
	}
	@Test
	public void "when unsuccesful save, version is same"(){
		VersionedKey version = 	new VersionedKey("key",1L)
		Mockito.when(mock.get(key)).thenReturn(Optional.of(version.toJson()))
		Mockito.when(mock.put(any(String),any(Object))).thenThrow(new RuntimeException("boo!"))
		try{
			manifestComparator.saveAndIncrement("data")
		}catch(Exception e){
		}

		assert manifestComparator.versionedKey == version.toJson()
	}
	@Test
	public void "when unsuccesful save, old version not deleted"(){
		VersionedKey version = 	new VersionedKey("key",1l)
		Mockito.when(mock.put(any(String),any(Object))).thenThrow(new RuntimeException("boo!"))
		try{
			manifestComparator.saveAndIncrement("data")
		}catch(Exception e){
		}

		Mockito.verify(mock,times(0)).delete(version.toJson())
	}
	@Test
	public void "when delete fails, version still incremented"(){
		VersionedKey version = 	new VersionedKey("key",1l)
		Mockito.when(mock.get(key)).thenReturn(Optional.of(version.toJson()))
		Mockito.when(mock.delete(any(String))).thenThrow(new RuntimeException("boo!"))
		try{
			manifestComparator.saveAndIncrement("data")
		}catch(Exception e){
		}
		Mockito.verify(mock,times(1)).put(eq(version.withVersion(2l).toJson()),any(Data))
		Mockito.verify(mock).put(key,version.withVersion(2l).toJson())
		assert manifestComparator.versionedKey == version.withVersion(2l).toJson()
	}

	@Test
	public void "when clean, old versions deleted"(){
		VersionedKey version = 	new VersionedKey("key",100L)
		Mockito.when(mock.get(key)).thenReturn(Optional.of(version.toJson()))
		manifestComparator.cleanAll()
		Mockito.verify(mock,times(100)).delete(any(String))
	}
}
