package aed.cache;

import es.upm.aedlib.Position;
import es.upm.aedlib.map.*;
import es.upm.aedlib.positionlist.*;

public class Cache<Key, Value> {
	private int maxCacheSize;
	private Storage<Key, Value> storage;
	private Map<Key, CacheCell<Key, Value>> map;
	private PositionList<Key> lru;

	public Cache(int maxCacheSize, Storage<Key, Value> storage) {
		this.storage = storage;
		this.map = new HashTableMap<Key, CacheCell<Key, Value>>();
		this.lru = new NodePositionList<Key>();
		this.maxCacheSize = maxCacheSize;
	}

	public Value get(Key key) {
		cambiarCache(key,storage.read(key));
		return map.get(key).getValue();
	}

	public void put(Key key, Value value) {
		cambiarCache(key,value);
		map.get(key).setValue(value);
		map.get(key).setDirty(true);
	}
	
	private void cambiarCache (Key key, Value value){
		if (map.get(key) != null) {
			lru.remove(map.get(key).getPos());
			lru.addFirst(key);
			map.get(key).setPos(lru.first());
		} else {
			lru.addFirst(key);
			CacheCell<Key, Value> cc = new CacheCell<Key, Value>(value, false, lru.first());
			map.put(key, cc);
			if (lru.size() > maxCacheSize) {
				Key u = lru.last().element();
				if (map.get(u).getDirty()) {
					storage.write(u, map.get(u).getValue());
				}
				map.remove(u);
				lru.remove(lru.last());
			}
		}
	}
}