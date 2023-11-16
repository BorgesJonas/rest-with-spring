package br.com.erudio.restwithspring.mapper

import org.modelmapper.ModelMapper

object DozerMapper {
    private val mapper: ModelMapper = ModelMapper()

    fun <O, D> parseObject(origin: O, destination: Class<D>?): D {
        return mapper.map(origin, destination)
    }

    fun <O, D> parseListObjects(origin: List<O>, destination: Class<D>?): ArrayList<D> {
        val destinationList: ArrayList<D> = ArrayList()

        for (o in origin) {
            destinationList.add(mapper.map(o, destination))
        }

        return destinationList
    }
}