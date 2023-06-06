using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
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
    public class khachhangsController : ApiController
    {
        private qlpizzaEntities14 db = new qlpizzaEntities14();

        // GET: api/khachhangs
        public IQueryable<khachhang> Getkhachhangs()
        {
            return db.khachhangs;
        }

        // GET: api/khachhangs/5
        [ResponseType(typeof(khachhang))]
        public IHttpActionResult Getkhachhang(string id)
        {
            khachhang khachhang = db.khachhangs.Find(id);
            if (khachhang == null)
            {
                return NotFound();
            }

            return Ok(khachhang);
        }
        //kiem tra 
        [HttpGet]
        [Route("kiemtra/{tk}")]
        public List<khachhang> getbytentg2(string tk)
        {
            String[] tach = tk.Split(',');
            String t = tach[0];
            String m = tach[1];
            List<khachhang> dsin = db.khachhangs.ToList();
            List<khachhang> dsout = new List<khachhang>();
            foreach (khachhang ls in dsin)
            {
                if (ls.tk.Contains(t)&& ls.mk.Contains(m))
                    dsout.Add(ls);
            }
            return dsout;
        }
        // PUT: api/khachhangs/5
        [ResponseType(typeof(void))]
        public IHttpActionResult Putkhachhang(string id, khachhang khachhang)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != khachhang.makh)
            {
                return BadRequest();
            }

            db.Entry(khachhang).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!khachhangExists(id))
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

        // POST: api/khachhangs
        [ResponseType(typeof(khachhang))]
        public IHttpActionResult Postkhachhang(khachhang khachhang)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.khachhangs.Add(khachhang);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (khachhangExists(khachhang.makh))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = khachhang.makh }, khachhang);
        }

        // DELETE: api/khachhangs/5
        [ResponseType(typeof(khachhang))]
        public IHttpActionResult Deletekhachhang(string id)
        {
            khachhang khachhang = db.khachhangs.Find(id);
            if (khachhang == null)
            {
                return NotFound();
            }

            db.khachhangs.Remove(khachhang);
            db.SaveChanges();

            return Ok(khachhang);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool khachhangExists(string id)
        {
            return db.khachhangs.Count(e => e.makh == id) > 0;
        }
    }
}