package Tracer

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec


class TopTest extends AnyFreeSpec with ChiselScalatestTester {
    "Tracer" in {
        test(new TracerTop()) {
            tr =>
                tr.clock.step(100)
        }
    }
}
