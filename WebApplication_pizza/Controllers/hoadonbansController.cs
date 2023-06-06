using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Globalization;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using WebApplication_pizza.Models;

namespace WebApplication_pizza.Controllers
{
    public class hoadonbansController : ApiController
    {
        private qlpizzaEntities14 db = new qlpizzaEntities14();

        // GET: api/hoadonbans
        public IQueryable<hoadonban> Gethoadonbans()
        {
            return db.hoadonbans;
        }

        // GET: api/hoadonbans/5
        [ResponseType(typeof(hoadonban))]
        public IHttpActionResult Gethoadonban(string id)
        {
            hoadonban hoadonban = db.hoadonbans.Find(id);
            if (hoadonban == null)
            {
                return NotFound();
            }

            return Ok(hoadonban);
        }
        //get hoa don tk
        [HttpGet]
        [Route("hoadb/{tentg}")]
        public List<hoadonban> getbytentg2(string tentg)
        {
            List<hoadonban> dsin = db.hoadonbans.ToList();
            List<hoadonban> dsout = new List<hoadonban>();
            foreach (hoadonban ls in dsin)
            {
                String a = "" + ls.makh;
                if (a.Contains(tentg)&& ls.tinhtrang!=("Đã nhận hàng"))
                    dsout.Add(ls);
            }
            return dsout;
        }
        //get tong gia 
        [HttpGet]
        [Route("thongk/{tentg}")]
        public IHttpActionResult getMasp(string tentg)
        {
            int g = int.Parse(tentg);
            List<hoadonban> dsin = db.hoadonbans.ToList();
            int tong = 0;
            int tong2 = 0;
            int tong3 = 0;
            int tong4 = 0;
            List<chitiethoadonban> dsin1 = db.chitiethoadonbans.ToList();
            foreach (hoadonban ls in dsin)
            {
                if (ls.ngaydat.Month == g)
                {
                    foreach (chitiethoadonban l in dsin1)
                    {
                        if (ls.mahdb.Contains(ls.mahdb))
                        {
                            String[] a = l.dongia.Split('k');
                            tong = tong + int.Parse(a[0]) * int.Parse(l.soluong);
                        }
                    }
                }
                if (ls.ngaydat.Month == (g-1))
                {
                    foreach (chitiethoadonban l in dsin1)
                    {
                        if (ls.mahdb.Contains(ls.mahdb))
                        {
                            String[] a = l.dongia.Split('k');
                            tong2 = tong2 + int.Parse(a[0]) * int.Parse(l.soluong);
                        }
                    }
                }
                if (ls.ngaydat.Month == (g-2))
                {
                    foreach (chitiethoadonban l in dsin1)
                    {
                        if (ls.mahdb.Contains(ls.mahdb))
                        {
                            String[] a = l.dongia.Split('k');
                            tong3 = tong3 + int.Parse(a[0]) * int.Parse(l.soluong);
                        }
                    }
                }
                if (ls.ngaydat.Month == (g-3))
                {
                    foreach (chitiethoadonban l in dsin1)
                    {
                        if (ls.mahdb.Contains(ls.mahdb))
                        {
                            String[] a = l.dongia.Split('k');
                            tong4 = tong4 + int.Parse(a[0]) * int.Parse(l.soluong);
                        }
                    }
                }

            }
            return Ok("/"+tong+"/"+tong2+"/"+tong3+"/"+tong4+"/");
        }
        //get hoa don tk
        [HttpGet]
        [Route("hoadbls/{tentg}")]
        public List<hoadonban> getlichsu(string tentg)
        {
            List<hoadonban> dsin = db.hoadonbans.ToList();
            List<hoadonban> dsout = new List<hoadonban>();
            foreach (hoadonban ls in dsin)
            {
                String a = "" + ls.makh;
                if (a.Contains(tentg) && ls.tinhtrang.Contains("Đã nhận hàng"))
                    dsout.Add(ls);
            }
            return dsout;
        }
        // PUT: api/hoadonbans/5
        [ResponseType(typeof(void))]
        public IHttpActionResult Puthoadonban(string id, hoadonban hoadonban)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != hoadonban.mahdb)
            {
                return BadRequest();
            }

            db.Entry(hoadonban).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!hoadonbanExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }
        // POST: api/hoadonbans
        [ResponseType(typeof(hoadonban))]
        public IHttpActionResult Posthoadonban(hoadonban hoadonban)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.hoadonbans.Add(hoadonban);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (hoadonbanExists(hoadonban.mahdb))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = hoadonban.mahdb }, hoadonban);
        }

        // DELETE: api/hoadonbans/5
        [ResponseType(typeof(hoadonban))]
        public IHttpActionResult Deletehoadonban(string id)
        {
            hoadonban hoadonban = db.hoadonbans.Find(id);
            if (hoadonban == null)
            {
                return NotFound();
            }

            db.hoadonbans.Remove(hoadonban);
            db.SaveChanges();

            return Ok(hoadonban);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool hoadonbanExists(string id)
        {
            return db.hoadonbans.Count(e => e.mahdb == id) > 0;
        }
    }
}