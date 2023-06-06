using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using csdl.Models;

namespace csdl.Controllers.API
{
    public class donhangsController : ApiController
    {
        private csdl1Entities db = new csdl1Entities();

        // GET: api/donhangs
        [HttpGet]
        [Route("api/donhang/Getdonhangs")]
        public IQueryable<donhang> Getdonhangs()
        {
            return db.donhangs;
        }
        [HttpGet]
        [Route("api/donhang/getadhllsp/{id}/{hanhchinh}")]
        public IHttpActionResult getadhllsp(int id, string hanhchinh)
        {
            var donhang = db.donhangs.Where(x => x.idtk == id && x.hanhchinh == hanhchinh);
            if (!donhang.Any())
            {
                return NotFound();
            }
            return Ok(donhang);

        }
        [HttpGet]
        [Route("api/ThongKeTaiKhoanTheoSoLuong/{Account}")]
        public IHttpActionResult ThongKeTheoTaiKhoanTheoSoLuong(int Account)
        {
            int tong = 0;
            // Lấy ra những đơn hàng có tình trạng tương ứng
            var orders = db.donhangs.Where(n => n.idtk == Account && n.hanhchinh == "Đã giao");

            
            foreach (var order in orders)
            {
                var orderDetails = db.giohangs.Where(x => x.idgh == order.idgh);
                foreach (var detail in orderDetails)
                {
                    // Assuming there is a product table and a relationship between order details and products based on idsanpham
                    tong += detail.sl;
                }
            }

            return Ok(tong);
        }
        [HttpGet]
        [Route("api/donhang/Getdonhangs/{id}")]
        public IHttpActionResult Getdonhang(int id)
        {
            donhang donhang = db.donhangs.Find(id);
            if (donhang == null)
            {
                return NotFound();
            }

            return Ok(donhang);
        }
        [HttpGet]
        [Route("api/donhang/tkdonhang")]
        public int thongkedh(string id)
        {
            db.donhangs.Where(x => x.hanhchinh == id);
            return db.san_pham.Count();
        }
        // PUT: api/donhangs/5
        [HttpPut]
        [Route("api/donhang/Putdonhang/{id}")]
        public IHttpActionResult Putdonhang(int id, donhang donhang)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != donhang.iddh)
            {
                return BadRequest();
            }

            db.Entry(donhang).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!donhangExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Ok(donhang);
        }

        // POST: api/donhangs
        [HttpPost]
        [Route("api/donhang/Postdonhang")]
        public IHttpActionResult Postdonhang(donhang donhang)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.donhangs.Add(donhang);
            db.SaveChanges();

            return Ok(donhang);
        }

        // DELETE: api/donhangs/5
        [HttpDelete]
        [Route("api/donhang/Deletedonhang/{id}")]
        public IHttpActionResult Deletedonhang(int id)
        {
            donhang donhang = db.donhangs.Find(id);
            if (donhang == null)
            {
                return NotFound();
            }

            db.donhangs.Remove(donhang);
            db.SaveChanges();

            return Ok(donhang);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool donhangExists(int id)
        {
            return db.donhangs.Count(e => e.iddh == id) > 0;
        }
    }
}