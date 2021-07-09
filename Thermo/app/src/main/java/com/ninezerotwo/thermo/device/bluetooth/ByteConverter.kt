package com.ninezerotwo.thermo.device.bluetooth

object ByteConverter {

    private fun toBinary(value: Int): String{
        var number = value
        if(value < 0) number+=255
        return String.format("%8s",Integer.toBinaryString(number)).replace(" ","0")
    }

    fun toTemperature(firstByte: Byte, secondByte: Byte): Float {
        var temp = Integer.parseInt(
            toBinary(firstByte.toInt()) +
                    toBinary(secondByte.toInt()), 2
        )
        return temp / 100.0f
    }

}