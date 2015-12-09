package com.aol.micro.server.couchbase;

import static org.junit.Assert.*
import net.spy.memcached.internal.OperationFuture


import com.couchbase.client.CouchbaseClient

class SimpleCouchbaseClientTest  extends spock.lang.Specification {
	
	
		def "test get() when cache match"() {
	
			CouchbaseClient mockMemcachedClient = Mock()
			String aKey = "1,2,3,4"
	
			def distributedCache = new DistributedMapClient(mockMemcachedClient)
			def result
	
			when:
			result = distributedCache.get(aKey)
	
			then:
			1 * mockMemcachedClient.get(aKey) >> Mock(MockEntity)
			result.isPresent()
		}
	
		def "test get() when cache miss"() {
	
			CouchbaseClient mockMemcachedClient = Mock()
			String aKey = "1,2,3,4"
			def distributedCache = new DistributedMapClient(mockMemcachedClient)
			def isPresent
	
			when:
			isPresent = distributedCache.get(aKey).isPresent()
	
			then:
			1 * mockMemcachedClient.get(_)
			!isPresent
		}
	
		def "test put with success"() {
			CouchbaseClient mockMemcachedClient = Mock()
			String aKey = "1,2,3,4"
			def distributedCache = new DistributedMapClient(mockMemcachedClient)
			def bitset = Mock(MockEntity)
			def putResult
	
			when:
			putResult = distributedCache.put(aKey, bitset)
	
			then:
			1 * mockMemcachedClient.set(aKey,  _) >> mockSuccessfulCachePutResult()
			putResult
		}
	
		def "test retries when first put fails"() {
	
			CouchbaseClient mockMemcachedClient = Mock()
			String aKey = "1,2,3,4"
			def distributedCache = new DistributedMapClient(mockMemcachedClient)
			def bitset = Mock(MockEntity)
			def putResult
	
			when:
			putResult = distributedCache.put(aKey, bitset)
	
			then:
			1 * mockMemcachedClient.set(aKey,  _) >>  mockSuccessfulCachePutResult()
			putResult
		}
	
		def "test when put fails"() {
	
			def maxRetries = 1
			CouchbaseClient mockMemcachedClient = Mock()
			String aKey = "1,2,3,4"
			def distributedCache = new DistributedMapClient(mockMemcachedClient)
			def bitset = Mock(MockEntity)
			def putResult
	
			when:
			putResult = distributedCache.put(aKey, bitset)
	
			then:
			maxRetries * mockMemcachedClient.set(aKey,  _) >> mockUnsuccessfulCachePutResult()
			!putResult
		}
	
	
		
		private mockSuccessfulCachePutResult() {
			def setResult = Mock(OperationFuture)
			setResult.get() >> true
			setResult
		}
	
		private mockUnsuccessfulCachePutResult() {
			def setResult = Mock(OperationFuture)
			setResult.get() >> false
			setResult
		}
	
	
	

}
