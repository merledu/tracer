package Tracer

import chisel3._

import Params._


class TracerTop extends Module {
    val tracer: TraceUnit = Module(new TraceUnit(Params.default))
}
