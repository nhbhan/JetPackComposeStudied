package com.hannhb.myapplication

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlin.random.Random

class DesignSystem {

}

class Laptop(
    var processor: String,
    var ram: Int,
    var battery: String,
    var os: String
)

class BuilderDesignPatter(private var processor: String) {
    private var _ram: Int = 0
    private var _battery: String = ""
    private var _os = "Window"

    fun setRam(ram: Int): BuilderDesignPatter {
        this._ram = ram
        return this
    }

    fun setBattery(battery: String): BuilderDesignPatter {
        this._battery = battery
        return this
    }

    fun setOperateSystem(operateSystem: String): BuilderDesignPatter {
        this._os = operateSystem
        return this
    }

    fun create() = Laptop(processor, _ram, _battery, _os)

}

class SingletonDesignPattern {
    companion object {
        private var instance: SingletonDesignPattern? = null
        fun init(): SingletonDesignPattern {
            var temptInstance = instance
            if (temptInstance != null) {
                return temptInstance
            }
            synchronized(this) {
                val _instance = SingletonDesignPattern()
                instance = _instance
                return _instance
            }
        }
    }
}

class SingletonDesignPatternModern {
    companion object {
        private val instance: SingletonDesignPatternModern by lazy { SingletonDesignPatternModern() }

        fun newInstance(): SingletonDesignPatternModern {
            return instance
        }
    }
}

interface Print {
    fun print() {
        println("abc")
    }
}
class TraditionalPrint(): Print {
    fun startPrint() {
        print("start print")
    }
}

class ModernPrint(val traditionalPrint: TraditionalPrint): Print {
    override fun print() {
        traditionalPrint.startPrint()
    }
}

interface Observer {
    fun update(value: Int)
}

class ValueObserver(private val name: String) : Observer {
    override fun update(value: Int) {
        println("$name receive $value")
    }

}

//class ValueSubject() {
//    val observers = MutableList<Observer>()
//    fun addObserver(_observer: Observer) {
//        observers.add(_observer)
//    }
//
//    fun removeObserver(_observer: Observer) {
//        observers.remove(_observer)
//    }
//
//    private fun notify(value: Int) {
//        for(observer in observers) {
//            observer.update(value)
//        }
//    }
//
//     private val observerbles: Flow<Int> = flow {
//        while(true){
//            emit(Random.nextInt(0, 100))
//            delay(100)
//        }
//    }
//
//    private fun starObserving(){
//        val observableCoroutine = CoroutineScope(this).launch {
//            observerbles.collect{it->
//                notify(it)
//            }
//        }
//    }
//}

//factory

interface Product {
    fun create(): String
}

// Concrete Product A
class ConcreteProductA : Product {
    override fun create(): String {
        return "Product A"
    }
}

// Concrete Product B
class ConcreteProductB : Product {
    override fun create(): String {
        return "Product B"
    }
}

// Factory interface
interface ProductFactory {
    fun createProduct(): Product
}

// Concrete Factory A
class ConcreteFactoryA : ProductFactory {
    override fun createProduct(): Product {
        return ConcreteProductA()
    }
}

// Concrete Factory B
class ConcreteFactoryB : ProductFactory {
    override fun createProduct(): Product {
        return ConcreteProductB()
    }
}

// Client code
fun main() {
    val factoryA: ProductFactory = ConcreteFactoryA()
    val productA: Product = factoryA.createProduct()
    println(productA.create())

    val factoryB: ProductFactory = ConcreteFactoryB()
    val productB: Product = factoryB.createProduct()
    println(productB.create())
}

