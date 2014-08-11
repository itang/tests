package test_guice

import java.util.{ Map => JMap }
import com.google.inject.Key
import com.google.inject.Provider
import com.google.inject.Scope
import com.google.common.collect.Maps
import com.google.inject.OutOfScopeException

object SimpleScope {
  private val SEEDED_KEY_PROVIDER: Provider[Any] = new Provider[Any]() {
    def get(): Any = {
      throw new IllegalStateException("If you got here then it means that" +
        " your code asked for scoped object which should have been" +
        " explicitly seeded in this scope by calling" +
        " SimpleScope.seed(), but was not.");
    }
  }

  def seededKeyProvider[T](): Provider[T] = {
    SEEDED_KEY_PROVIDER.asInstanceOf[Provider[T]];
  }
}

class SimpleScope extends Scope {

  private val values: ThreadLocal[JMap[Key[_], Any]] = new ThreadLocal[JMap[Key[_], Any]]()

  def enter() {
    assert(values.get() == null, "A scoping block is already in progress")
    values.set(Maps.newHashMap());
  }

  def exit() {
    assert(values.get() != null, "No scoping block in progress")
    values.remove()
  }

  def seed[T](key: Key[T], value: T) {
    val scopedObjects: JMap[Key[_], Any] = getScopedObjectMap(key)
    assert(!scopedObjects.containsKey(key), s"A value for the key $key was already seeded in this scope. Old value: ${scopedObjects.get(key)} New value: $value")

    scopedObjects.put(key, value)
  }

  def seed[T](clazz: Class[T], value: T) {
    seed(Key.get(clazz), value)
  }

  def scope[T](key: Key[T], unscoped: Provider[T]): Provider[T] = {
    return new Provider[T]() {
      def get(): T = {
        val scopedObjects: JMap[Key[_], Any] = getScopedObjectMap(key);

        var current: T = scopedObjects.get(key).asInstanceOf[T]
        if (current == null && !scopedObjects.containsKey(key)) {
          println("unscoped.get")
          current = unscoped.get()
          scopedObjects.put(key, current)
        } else {
          println("scopedObjects.get")
        }
        return current;
      }
    }
  }

  private def getScopedObjectMap[T](key: Key[T]): JMap[Key[_], Any] = {
    val scopedObjects: JMap[Key[_], Any] = values.get();
    if (scopedObjects == null) {
      throw new OutOfScopeException("Cannot access " + key
        + " outside of a scoping block")
    }
    return scopedObjects
  }
}
