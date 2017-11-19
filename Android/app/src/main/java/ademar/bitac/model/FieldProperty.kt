package ademar.bitac.model

import java.util.*
import kotlin.reflect.KProperty

class FieldProperty<in Clazz, Field : Any> {

    private val map = WeakHashMap<Clazz, Field>()

    operator fun getValue(thisRef: Clazz, property: KProperty<*>): Field {
        return map[thisRef] ?: throw UninitializedPropertyAccessException()
    }

    operator fun setValue(thisRef: Clazz, property: KProperty<*>, value: Field): Field {
        map[thisRef] = value
        return value
    }

}
