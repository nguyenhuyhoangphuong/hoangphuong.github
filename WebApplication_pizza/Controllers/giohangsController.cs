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
using WebApplication_pizza.Models;

namespace WebApplication_pizza.Controllers
{
    public class giohangsController : ApiController
    {
        private qlpizzaEntities14 db = new qlpizzaEntities14();

        // GET: api/giohangs
        public IQueryable<giohang> Getgiohangs()
        {
            return db.giohangs;
        }

        // GET: api/giohangs/5
        [ResponseType(typeof(giohang))]
        public IHttpActionResult Getgiohang(int id)
        {
            giohang giohang = db.giohangs.Find(id);
            if (giohang == null)
            {
                return NotFound();
            }

            return Ok(giohang);
        }

        // PUT: api/giohangs/5
        [ResponseType(typeof(void))]
        public IHttpActionResult Putgiohang(int id, giohang giohang)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != giohang.matk)
            {
                return BadRequest();
            }

            db.Entry(giohang).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!giohangExists(id))
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
        [HttpGet]
        [Route("giohangs2/{tentg}")]
        public List<giohang> getbytentg2(string tentg)
        {
            List<giohang> dsin = db.giohangs.ToList();
            List<giohang> dsout = new List<giohang>();
            foreach (giohang ls in dsin)
            {
                String a = "" + ls.matk;
                if (a.Contains(tentg))
                    dsout.Add(ls);
            }
            return dsout;
        }
        // POST: api/giohangs
        [ResponseType(typeof(giohang))]
        public IHttpActionResult Postgiohang(giohang giohang)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.giohangs.Add(giohang);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (giohangExists(giohang.matk))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = giohang.matk }, giohang);
        }

        // DELETE: api/giohangs/5
        [ResponseType(typeof(giohang))]
        public IHttpActionResult Deletegiohang(int id)
        {
            giohang giohang = db.giohangs.Find(id);
            if (giohang == null)
            {
                return NotFound();
            }

            db.giohangs.Remove(giohang);
            db.SaveChanges();

            return Ok(giohang);
        }
        // delete gior hang
        [HttpGet]
        [Route("xoagiohangs2/{tentg}")]
        public IHttpActionResult XoaGiohang(String tentg)
        {
            String[] a = tentg.Split(',');
            String matk = a[0];
            String masp = a[1];
            List<giohang> dsout = new List<giohang>();
            List<giohang> dsin = db.giohangs.ToList();
            foreach (giohang ls in dsin)
            {
                String ma = "" + ls.matk;
                if (ma.Contains(matk)&& ls.masp.Contains(masp))
                {
                 db.giohangs.Remove(ls);
                 db.SaveChanges();
                 dsout.Add(ls);

                }
            }
            return Ok(dsout);
        }
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool giohangExists(int id)
        {
            return db.giohangs.Count(e => e.matk == id) > 0;
        }
    }
}