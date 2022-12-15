package Tracer

import chisel3._

import Params._


class TracerTop extends Module {
    val tracer: Tracer = Module(new Tracer(Params.default))
}
